package com.onlinebankingsystem.utility;

import java.util.Random;
import java.util.UUID;

public class TransactionIdGenerator {
	
	public static String generate() {
		UUID uuid = UUID.randomUUID();
        String uuidHex = uuid.toString().replace("-", ""); // Remove hyphens
        String uuid16Digits = uuidHex.substring(0, 16); // Take the first 16 characters
        
        return uuid16Digits;
    }
	
	public static String generateAccountId() {
		UUID uuid = UUID.randomUUID();
        String uuidHex = uuid.toString().replace("-", ""); // Remove hyphens
        String accountId = uuidHex.substring(0, 9); // Take the first 16 characters
        
        return accountId;
    }
	
	public static String generatePassword() {
		UUID uuid = UUID.randomUUID();
        String uuidHex = uuid.toString().replace("-", ""); // Remove hyphens
        String random = uuidHex.substring(0, 4); // Take the first 16 characters
        
        String password = "Bank@"+random;
        
        return password;
    }
	
	public static String generateUniqueTransactionRefId() {
        // Generate a random number between 1000000000 and 9999999999
        Random random = new Random();
        long randomNum = random.nextInt(900000000) + 1000000000;
        
        // Get current timestamp
        long timestamp = System.currentTimeMillis() % 1000000000;
        
        // Combine timestamp and random number
        String uniqueId = String.valueOf(timestamp) + String.valueOf(randomNum);
        
        // Trim or pad with zeros to make sure the ID is 10 digits long
        if (uniqueId.length() > 10) {
            uniqueId = uniqueId.substring(0, 10);
        } else if (uniqueId.length() < 10) {
            while (uniqueId.length() < 10) {
                uniqueId = "0" + uniqueId;
            }
        }
        
        return uniqueId;
    }

}
