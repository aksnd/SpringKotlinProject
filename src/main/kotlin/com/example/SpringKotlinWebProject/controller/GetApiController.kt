package com.example.SpringKotlinWebProject.controller

import com.example.SpringKotlinWebProject.model.UserRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class GetApiController {
    @GetMapping(path = ["/hello","/abcd"])
    fun hello():String{
        return "hello kotlin"
    }

    @RequestMapping(method = [RequestMethod.GET], path = ["/request-mapping"])
    fun requestMapping(): String{
        return "request-mapping"
    }

    @GetMapping("/get-mapping/path-variable/{name}") //GET http://localhost:8080/api/get-mapping/path-variable/steve
    fun pathVariable(@PathVariable name: String): String{
        println(name)
        return name
    }

    @GetMapping("/getUserInfo/{name}/{age}") //GET http://localhost:8080/api/getUserInfo/Jihun/23
    fun pathVariable(@PathVariable(value = "name") new_name: String, @PathVariable age:Int): String{
        val name = "none"
        println("${new_name}, ${age}")
        return "$new_name $age"
    }

    //http://localhost:8008/api/page?key=value&key=value&key=value
    @GetMapping("get-mapping/query-param")
    fun queryParam(
        @RequestParam name:String,
        @RequestParam(value = "age") age:Int
    ): String{
        println("${name}, ${age}")
        return "$name $age"
    }


    //여러가지 값을 받을때
    @GetMapping("get-mapping/query-param/user")
    fun queryParamGameInfo(userRequest: UserRequest): UserRequest {
        println(userRequest)
        return userRequest
    }

    @GetMapping("get-mapping/query-param/map")
    fun queryParamMap(@RequestParam map:Map<String, Any>):Map<String,Any>{
        println(map)
        val phoneNumber = map.get("phone-number")
        return map
    }

}