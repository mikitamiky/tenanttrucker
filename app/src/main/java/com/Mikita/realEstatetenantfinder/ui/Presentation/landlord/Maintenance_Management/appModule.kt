package com.Mikita.realEstatetenantfinder.ui.Presentation.landlord.Maintenance_Management

import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

interface MessagingService {
    fun sendMessage(requestId: String, message: Message)
}

data class Message(
    val content: String,
    val senderId: String,
    val timestamp: Long
)

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideMessagingService(database: FirebaseDatabase): MessagingService {
        return object : MessagingService {
            override fun sendMessage(requestId: String, message: Message) {
                database.getReference("messages/$requestId")
                    .push()
                    .setValue(message)
            }
        }
    }
}