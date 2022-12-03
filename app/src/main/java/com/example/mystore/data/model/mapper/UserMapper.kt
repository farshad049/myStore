package com.example.mystore.data.model.mapper

import com.example.mystore.data.model.domain.Address
import com.example.mystore.data.model.domain.DomainUser
import com.example.mystore.data.model.domain.Name
import com.example.mystore.data.model.network.NetworkUser
import com.example.mystore.util.capitalize
import javax.inject.Inject


class UserMapper @Inject constructor() {

    fun buildFrom(networkUser : NetworkUser) : DomainUser {
        return DomainUser(
            id = networkUser.id ,
            name =
            Name(
                firstname = networkUser.name.firstname.capitalize() ,
                lastname = networkUser.name.lastname.capitalize()
            ) ,
            email = networkUser.email,
            username = networkUser.username ,
            phoneNumber = networkUser.phone ,
            address =
            Address(
                city = networkUser.address.city,
                number = networkUser.address.number,
                street = networkUser.address.street,
                zipcode = networkUser.address.zipcode,
                lat = networkUser.address.geolocation.lat,
                long = networkUser.address.geolocation.long
            ),
            greetingMessage = "welcome ${networkUser.name.firstname.capitalize()}"

        )

    }

}


