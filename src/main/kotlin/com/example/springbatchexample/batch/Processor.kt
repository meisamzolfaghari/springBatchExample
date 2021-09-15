package com.example.springbatchexample.batch

import com.example.springbatchexample.entity.User
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class Processor: ItemProcessor<User, User> {

    companion object {
        val DEPT_NAMES: Map<String, String> =
            hashMapOf(Pair("001", "Technology"), Pair("002", "operations"), Pair("003", "Accounts"))
    }

    override fun process(user: User): User? {

        val deptCode = user.department;

        user.apply { department = DEPT_NAMES[deptCode]!! }

        println("converted from $deptCode to ${user.department}")

        return user;
    }
}