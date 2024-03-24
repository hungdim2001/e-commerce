package com.example.core.service;

import com.example.core.configuration.VNPayConfig;
import com.example.core.dto.PaymentResDTO;
import com.example.core.entity.Order;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VnPayService {
    public String  createPayment(Long price, Long orderId, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.VNP_VERSION);
        vnp_Params.put("vnp_Command", VNPayConfig.VNP_COMMAND);
        vnp_Params.put("vnp_TmnCode", VNPayConfig.TMN_CODE);
        vnp_Params.put("vnp_Amount", String.valueOf(price));
        vnp_Params.put("vnp_CurrCode", VNPayConfig.CURRCODE);
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", String.valueOf(orderId)); // Order id
        vnp_Params.put("vnp_OrderInfo", "Ok");
        vnp_Params.put("vnp_OrderType", VNPayConfig.ORDER_TYPE);
        vnp_Params.put("vnp_Locale", VNPayConfig.LOCATE);
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.RETURNURL);
        vnp_Params.put("vnp_IpAddr", VNPayConfig.getIpAddress(httpServletRequest));
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.CHECKSUM, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.VNP_PAYURL + "?" + queryUrl;
        return  paymentUrl;
    }
    public int orderReturn(HttpServletRequest request){
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = null;
            String fieldValue = null;
            try {
                fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = VNPayConfig.hashAllFields(fields);
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }
//    public void checkOrder(HttpServletRequest httpServletRequest) throws IOException {
//        String vnp_RequestId = VNPayConfig.getRandomNumber(8);
//        String vnp_Version = "2.1.0";
//        String vnp_Command = "refund";
//        String vnp_TmnCode = VNPayConfig.TMN_CODE;
////        String vnp_TransactionType = httpServletRequest.getParameter("trantype");
//        String vnp_TxnRef = httpServletRequest.getParameter("vnp_TxnRef");
//        long amount = Integer.parseInt(httpServletRequest.getParameter("vnp_Amount"));
//        String vnp_Amount = String.valueOf(amount);
//        String vnp_OrderInfo = "Hoan tien GD OrderId:" + vnp_TxnRef;
//        String vnp_TransactionNo = ""; //Assuming value of the parameter "vnp_TransactionNo" does not exist on your system.
////        String vnp_TransactionDate = httpServletRequest.getParameter("trans_date");
////        String vnp_CreateBy = httpServletRequest.getParameter("user");
//
//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
////        String vnp_CreateDate = formatter.format(cld.getTime());
//
//        String vnp_IpAddr = VNPayConfig.getIpAddress(httpServletRequest);
//
//        JsonObject vnp_Params = new JsonObject ();
//
//        vnp_Params.addProperty("vnp_RequestId", vnp_RequestId);
//        vnp_Params.addProperty("vnp_Version", vnp_Version);
//        vnp_Params.addProperty("vnp_Command", vnp_Command);
//        vnp_Params.addProperty("vnp_TmnCode", vnp_TmnCode);
////        vnp_Params.addProperty("vnp_TransactionType", vnp_TransactionType);
//        vnp_Params.addProperty("vnp_TxnRef", vnp_TxnRef);
//        vnp_Params.addProperty("vnp_Amount", vnp_Amount);
//        vnp_Params.addProperty("vnp_OrderInfo", vnp_OrderInfo);
//
//        if(vnp_TransactionNo != null && !vnp_TransactionNo.isEmpty())
//        {
//            vnp_Params.addProperty("vnp_TransactionNo", "{get value of vnp_TransactionNo}");
//        }
//
////        vnp_Params.addProperty("vnp_TransactionDate", vnp_TransactionDate);
////        vnp_Params.addProperty("vnp_CreateBy", vnp_CreateBy);
////        vnp_Params.addProperty("vnp_CreateDate", vnp_CreateDate);
////        vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);
//
//        String hash_Data= String.join("|", vnp_RequestId, vnp_Version, vnp_Command, vnp_TmnCode,
//                , vnp_TxnRef, vnp_Amount, vnp_TransactionNo,
//                 vnp_IpAddr, vnp_OrderInfo);
//
//        String vnp_SecureHash = VNPayConfig.hmacSHA512("", hash_Data.toString());
//
//        vnp_Params.addProperty("vnp_SecureHash", vnp_SecureHash);
//
//        URL url = new URL (VNPayConfig.VNP_APIURL);
//        HttpURLConnection con = (HttpURLConnection)url.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Content-Type", "application/json");
//        con.setDoOutput(true);
//        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//        wr.writeBytes(vnp_Params.toString());
//        wr.flush();
//        wr.close();
//        int responseCode = con.getResponseCode();
//        System.out.println("nSending 'POST' request to URL : " + url);
//        System.out.println("Post Data : " + vnp_Params);
//        System.out.println("Response Code : " + responseCode);
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String output;
//        StringBuffer response = new StringBuffer();
//        while ((output = in.readLine()) != null) {
//            response.append(output);
//        }
//        in.close();
//        System.out.println(response.toString());
//
//    }
}
