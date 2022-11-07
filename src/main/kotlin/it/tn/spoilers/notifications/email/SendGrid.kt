package it.tn.spoilers.notifications.email


import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import java.io.IOException


/**
 * Class for sending emails using SendGrid
 *
 * @author Francesco Masala
 */
class EmailSender {
    /**
     * Function for sending an email
     *
     * @param email[String] the email address of the recipient
     * @param subject[String] the subject of the email
     * @param EmailText[String] the content of the email
     */
    fun sendEmail(email: String, subject: String, EmailText: String) {
        val from = Email("noreply@bookfinder.spoilers.tn.it")
        val to = Email(email)
        val content = Content("text/plain", EmailText)
        val mail = Mail(from, subject, to, content)

        val sg = SendGrid("")
        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()
            val response = sg.api(request)
            System.out.println(response.statusCode)
            System.out.println(response.body)
            System.out.println(response.headers)
        } catch (ex: IOException) {
            throw ex
        }
    }
}