package it.tn.spoilers.notifications.email


import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import java.io.IOException


class EmailSender {
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