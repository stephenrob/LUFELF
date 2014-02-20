package uk.co.stephen_robinson.uni.lufelf;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by James on 19/02/2014.
 */
public class ValidationChecker {

    /**
     * Check if the EditText is empty
     * Also adds the error message to the edittext
     * @param editText the edittext to be checked
     * @return if the string is empty,returns true - otherwise false.
     */
    public static boolean checkIfEmpty(EditText editText){
        boolean result=editText.getText().toString().compareTo("")==0;
        if(result)
            editText.setError("This field cannot be empty!");
        return result;
    }

    /**
     * Check if the string is empty
     * @param text the string to be checked
     * @return if the string is empty,returns true - otherwise false.
     */
    public static boolean checkIfEmpty(String text){
        return text.compareTo("")==0;
    }

    /**
     * Check the EditText field for characters other than a-z and 0-9
     * Also adds the error message to the edittext
     * @param editText the edittext to be checked
     * @return if there are other characters than a-z and 0-9 - will return false
     */
    public static boolean noOddCharacters(EditText editText){
        resetEditText(editText);
        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(editText.getText().toString());
        boolean result=!matcher.find();
        if(!result)
            editText.setError("Your "+editText.getHint()+" can only use the characters a-z and 0-9");
        return result;
    }

    /**
     * Check the String field for characters other than a-z and 0-9
     * @param text the string to be checked
     * @return if there are other characters than a-z and 0-9 - will return true
     */
    public static boolean noOddCharacters(String text){
        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return !matcher.find();
    }
    /**
     * check if the EditText is greater than a certain size
     * Also adds the error message to the edittext
     * @param text the EditText to be checked
     * @param size the size to check the string against
     * @return true if the size is greater than or equal to the target size
     */
    public static boolean checkSize(EditText text,int size){
        boolean result=text.getText().toString().length()>=size;
        if(!result)
            text.setError("Your "+text.getHint()+" should be longer than "+size+" characters.");
        return result;
    }
    /**
     * check if the string is greater than a certain size
     * @param text the string to be checked
     * @param size the size to check the string against
     * @return true if the size is greater than or equal to the target size
     */
    public static boolean checkSize(String text,int size){
        return text.length()>=size;
    }
    /**
     * method is used for checking valid email id format.
     *Also adds the error message to the edittext
     * @param email the edittext to check
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(EditText email) {
        resetEditText(email);
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email.getText().toString();

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        if(!isValid)
            email.setError("This email address is not valid!");
        return isValid;
    }
    /**
     * method is used for checking valid email id format.
     *
     * @param email the email string to check
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * This method is used to compare two text fields' value.
     * Should be primarily used on password fields.
     * @param field1 the first field to check
     * @param field2 the second field to check
     * @return true if both fields are the same
     */
    public static boolean fieldsHaveSameValue(EditText field1, EditText field2){

        boolean isSame =field1.getText().toString().equals(field2.getText().toString());
        if(!isSame){
            field1.setError(field1.getHint()+" needs to be the same as "+field2.getHint());
            field2.setError(field2.getHint()+" needs to be the same as "+field1.getHint());
            return isSame;
        }
        return isSame;
    }
    public static boolean standardValidationCheck(EditText[] editTexts){
        //validate entries
        boolean allOk=true;
        for(int i=0;i<editTexts.length;i++){
            resetEditText(editTexts[i]);
            if(allOk)
                allOk=!ValidationChecker.checkIfEmpty(editTexts[i]);
            else
                ValidationChecker.checkIfEmpty(editTexts[i]);
            if(allOk)
                allOk=ValidationChecker.checkSize(editTexts[i], 3);
            else
                ValidationChecker.checkSize(editTexts[i], 3);
        }
        return allOk;
    }
    public static void resetEditText(EditText editText){
        editText.setError(null);
    }
}
