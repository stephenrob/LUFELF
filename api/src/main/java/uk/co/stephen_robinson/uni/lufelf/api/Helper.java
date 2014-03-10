package uk.co.stephen_robinson.uni.lufelf.api;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Stephen on 22/02/2014.
 */

/**
 * @author stephen
 * Provides overall helper methods to the api package
 */
public class Helper {

    /**
     *
     * @param s string to encode in md5
     * @return Returns encoded md5 string or null string if there is an error
     */

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
