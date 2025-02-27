package com.example.messengerapp.presentation.viewmodel

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.data.AuthData
import com.example.messengerapp.data.dto.UserDto
import com.example.messengerapp.data.CountryData
import com.example.messengerapp.domain.usecases.LoginUseCase
import com.example.messengerapp.domain.models.User
import com.example.messengerapp.domain.repository.AuthRepository
import com.example.messengerapp.domain.repository.RegistrationRepository
import com.example.messengerapp.domain.usecases.LogOutUseCase
import com.example.messengerapp.util.CountriesUtils
import com.example.messengerapp.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val registrationRepository: RegistrationRepository,
    private val loginUseCase: LoginUseCase,
    private val logOutUseCase: LogOutUseCase
): ViewModel()  {

    val user = loginUseCase.userFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = null
    )

    val authData: MutableState<AuthData?> = mutableStateOf(null)

    private val _userExists = MutableStateFlow<Boolean?>(null)
    val userExists = _userExists.asStateFlow()

//    private val _currentUser = MutableStateFlow<User?>(null)
//    val currentUser = _currentUser.asStateFlow()

    val userNumber : MutableState<String?> = mutableStateOf(null)

    private val rootCountryList = CountriesUtils.countriesList
    val countriesDataList : MutableState<List<CountryData>> = mutableStateOf(rootCountryList)

    private val _currentCountry = MutableStateFlow<CountryData?>(null)
    val currentCountry = _currentCountry.asStateFlow()


    private val _fullPhoneNumber = MutableStateFlow<String?>(null)
    val fullPhoneNumber = _fullPhoneNumber.asStateFlow()


    fun signInWithCredential(
        otp: String
    ): Flow<ResultState<String>> {
        return authRepository.signWithCredential(otp = otp)
    }

    fun signUpUserWithPhoneNumber(
        phoneNumber: String,
        activity: Activity
    ): Flow<ResultState<String>> {
       return authRepository.verifyPhoneNumberWithOtp(phoneNumber, activity)
    }


    fun uploadImage(imageUri: Uri, userId: String): Flow<ResultState<String>> {
        return registrationRepository.uploadImage(imageUri,userId)
    }


    fun insertUser(user: UserDto): Flow<ResultState<String>> {
        return registrationRepository.insert(user)
    }

    fun checkUserExists(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.checkUserExists(phoneNumber).collect{ userExists ->
                Log.d("user_exists", "$userExists")
                _userExists.value = userExists
            }
        }
    }

    fun getCurrentUser(phoneNumber: String) {
        Log.d("user_phone (getCurrentUser)", phoneNumber)
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.getCurrentUser(phoneNumber = phoneNumber).collect { currentUser ->
                when (currentUser) {
                    is ResultState.Loading -> {
                        Log.d("user_info", "Loading...")
                    }
                    is ResultState.Success -> {
                        Log.d("user_info", "${currentUser.data}")
//                        _currentUser.value =  currentUser.data
//                        Log.d("user_info", "${_currentUser.value}")
                        loginUseCase.invoke(phoneNumber = phoneNumber)
                    }
                    is ResultState.Error -> {}
                }
            }
        }
    }


    fun getAuthData(){
        viewModelScope .launch(Dispatchers.IO) {
            authData.value = registrationRepository.getRegistrationAuthData()
        }
    }

     suspend fun logOut(): Flow<ResultState<String>> {
        val logoutResult =  viewModelScope.async(Dispatchers.IO) {
             logOutUseCase.invoke()
         }.await()
//        val logOutResult = viewModelScope.async(Dispatchers.IO) {
//            authRepository.logOut()
//        }.await()
//
//        return logOutResult
         return logoutResult
    }

    fun findCountryCode(query: String) {
        countriesDataList.value = rootCountryList.filter { countryData ->
            countryData.countryName.contains(query, ignoreCase = true)
        }
    }

    fun setCountry(countryData: CountryData) {
        _currentCountry.value = countryData
    }

    fun setUserNumber(number: String){
        val countryCode = currentCountry.value?.countryPhoneCode
        _fullPhoneNumber.value = "$countryCode$number"
        Log.d("numberCurr", "${_fullPhoneNumber.value}, ${_fullPhoneNumber.value?.length}")
    }

}