package uk.co.stephen_robinson.uni.lufelf.utilities;

/**
 * @author James
 * dialog callback for the dialog windows
 */
public interface DialogCallback {
    /**
     * the callback used for dialog windows
     * @param result the integer result of the selected option
     */
    void messageComplete(int result);
}
