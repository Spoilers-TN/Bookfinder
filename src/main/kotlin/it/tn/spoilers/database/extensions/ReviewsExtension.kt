package it.tn.spoilers.database.extensions

import it.tn.spoilers.database.models.Reviews
import it.tn.spoilers.database.models.ReviewsData

fun Reviews.toReviewsData(): ReviewsData =
    ReviewsData(
        Review_ID = this.Review_ID,
        Review_Title = this.Review_Title,
        Review_Message = this.Review_Message,
        Review_Sender = this.Review_Sender,
        Review_Recipient = this.Review_Recipient
    )

fun ReviewsData.toReviews(): Reviews =
    Reviews(
        Review_ID = this.Review_ID,
        Review_Title = this.Review_Title,
        Review_Message = this.Review_Message,
        Review_Sender = this.Review_Sender,
        Review_Recipient = this.Review_Recipient
    )
