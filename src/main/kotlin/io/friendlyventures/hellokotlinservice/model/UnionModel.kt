package io.friendlyventures.hellokotlinservice.model

interface BodyPart

data class LeftHand(val field: String) : BodyPart

data class RightHand(val property: Int) : BodyPart
