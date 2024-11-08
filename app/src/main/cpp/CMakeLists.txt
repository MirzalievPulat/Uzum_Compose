cmake_minimum_required(VERSION 3.22.1)
#${CMAKE_PROJECT_NAME} o’zgaruvchiga qiymatni o’rnatish, top level o’zgaruvchi sifatida ishlatish mumkin.
project("bootcamp")
#Native kutubxona kodini SHARED ruxsat bilan projectga qo’shish
add_library(${CMAKE_PROJECT_NAME} SHARED bootcamp-lib.cpp)
#Log chiqarish uchun android logni ulash
target_link_libraries(${CMAKE_PROJECT_NAME} android log)