package it.tn.spoilers.notifications.email

import com.sendgrid.SendGrid
import it.tn.spoilers.database.models.AnnouncementsData
import it.tn.spoilers.database.models.UsersData

/**
 * Class for sending email to users
 *
 * @author Francesco Masala (francesco.masala@buonarroti.tn.it)
 * @author Damiano Berasi (damiano.berasi@buonarroti.tn.it)
 * @version 1.0
 *
 */
class UserEmails {
    /**
     * Function for sending an email
     *
     * @param email[String] the email address of the recipient
     * @param subject[String] the subject of the email
     * @param EmailText[String] the content of the email
     */
    fun BookBuyedEmail(seller:UsersData, buyer:UsersData, announcement: AnnouncementsData){
        val sellerEmail = seller.User_Email
        val buyerEmail = buyer.User_Email

        val announcementCode = announcement.id.toString()
        val SelectedBook = announcement.Announcement_Book
        val SelectedBookPrice = announcement.Announcement_Price

        val text = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Email in arrivo</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "        <style>\n" +
                "            table {\n" +
                "                border-collapse: collapse;\n" +
                "                width: 100%;\n" +
                "                max-width: 600px;\n" +
                "                margin: 0 auto;\n" +
                "            }\n" +
                "            td {\n" +
                "                padding: 10px;\n" +
                "                border: 1px solid #ccc;\n" +
                "\n" +
                "            }\n" +
                "            th{\n" +
                "                padding:20px;\n" +
                "            }\n" +
                "        </style>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <table style=\"border:2px solid black; margin-top: 5%\">\n" +
                "            <tr style=\"background: #343a40;\">  \n" +
                "                <th colspan=\"2\" style=\"text-align: center\">\n" +
                "                    <a style=\"color: var(--light);font-weight: bold; font-weight: bold; color: #FFDE59; font-size: 23px\">BookFinder</a></td>\n" +
                "                </th>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"width: 15%\"><b>Da:</b></td>\n" +
                "                <td>" + buyerEmail + "</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td><b>Oggetto:</b></td>\n" +
                "                <td>Richiesta informazioni per: " + SelectedBook + "</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td><b>Contenuto:</b></td>\n" +
                "                <td> ... </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td colspan=\"2\" style=\"text-align: center\"><a class=\"btn btn-warning\" style=\"width: 15%\" href=\"mailto: " + buyerEmail + "\">Rispondi</a></td>\n" +
                "\n" +
                "            </tr>\n" +
                "\n" +
                "        </table>\n" +
                "\n" +
                "        \n" +
                "    </body>\n" +
                "</html>"

    }
}
