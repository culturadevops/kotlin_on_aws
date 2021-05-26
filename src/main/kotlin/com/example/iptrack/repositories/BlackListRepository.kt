package com.example.iptrack.repositories

import com.example.iptrack.entities.Blacklist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlackListRepository: JpaRepository<Blacklist, String>