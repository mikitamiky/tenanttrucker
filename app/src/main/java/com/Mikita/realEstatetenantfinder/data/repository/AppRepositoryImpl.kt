//package com.Mikita.realEstatetenantfinder.data.repository
//
//import com.Mikita.realEstatetenantfinder.domain.model.User
//import com.Mikita.realEstatetenantfinder.domain.repository.AppRepository
//import com.google.firebase.auth.FirebaseAuth
//
//import kotlinx.coroutines.tasks.await
//
//class AppRepositoryImpl(
//    private val firebaseAuth: FirebaseAuth
//) : AppRepository {
//
//    // 🔐 Used by LoginScreen
//    override suspend fun loginUser(email: String, password: String): User? {
//        return try {
//            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
//            result.user?.let { firebaseUser ->
//                User(
//                    uid = firebaseUser.uid,
//                    email = firebaseUser.email ?: "",
//                    profileImageUrl = firebaseUser.photoUrl?.toString() ?: ""
//                )
//            }
//        } catch (e: Exception) {
//            null
//        }
//    }
//
//    // 👤 Used by ProfileScreen to get the logged-in user's info
//    override suspend fun getCurrentUser(): User? {
//        val firebaseUser = firebaseAuth.currentUser
//        return firebaseUser?.let {
//            User(
//                uid = it.uid,
//                email = it.email ?: "",
//                profileImageUrl = it.photoUrl?.toString() ?: ""
//            )
//        }
//    }
//
//    // 🚪 Used by ProfileScreen for logging out
//    override suspend fun logoutUser() {
//        firebaseAuth.signOut()
//    }
//}
