package com.example.springbatchexample.batch

import com.example.springbatchexample.entity.User
import com.example.springbatchexample.repository.UserRepository
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class DBWriter(private val userRepository: UserRepository): ItemWriter<User> {

    override fun write(users: MutableList<out User>) {
        println("data saved for user: $users")
        userRepository.saveAll(users)
    }
}