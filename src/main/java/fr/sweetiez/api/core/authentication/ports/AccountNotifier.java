package fr.sweetiez.api.core.authentication.ports;

public interface AccountNotifier {

    /**
     * Send the reset password link to the user.
     * @param email the user email
     * @param resetPasswordToken the reset password token
     */
    void sendResetPasswordLink(String email, String resetPasswordToken);

    /**
     * Notify the user that his password has been changed.
     * @param email
     */
    void notifyPasswordChange(String email);

    /**
     * Notify the user that his account has been created.
     * @param email
     */
    void notifyAccountCreation(String email);

}
