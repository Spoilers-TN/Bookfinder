package it.tn.spoilers.plugins.database

import it.tn.spoilers.database.models.Image
import it.tn.spoilers.database.models.ImageData


fun Image.toImageData(): ImageData =
    ImageData(
        id = this.id.toString(),
        filename =  this.filename,
        content = this.content
    )

fun ImageData.toImage(): Image =
    Image(
        id = this.id,
        filename =  this.filename,
        content = this.content
    )