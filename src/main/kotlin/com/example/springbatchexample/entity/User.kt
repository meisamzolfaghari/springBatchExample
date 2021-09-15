package com.example.springbatchexample.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class User(

    @Id
    var id: Int,

    var name: String,

    var department: String,

    var salary: Int,
)