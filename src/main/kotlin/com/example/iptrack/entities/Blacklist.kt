package com.example.iptrack.entities

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Blacklist {
    @Id
    var ip: String? = null
}