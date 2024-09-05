/* 
Nome: Erik Samuel Viana Hsu         RA: 10403109
Nome: Thiago Shihan Cardoso Toma    RA: 10400764
*/

#include <iostream> 
#include <thread>
#include <mutex>


float saldo = 1000.00;
std::mutex mtx;

float depositos(int qtd, float valor){
    std::lock_guard<std::mutex> lock(mtx);
    saldo = saldo + (qtd * valor);
    return saldo;
}

float saques(int qtd, float valor){
    std::lock_guard<std::mutex> lock(mtx);
    saldo = saldo - (qtd * valor);
    return saldo;
}


int main(){

    std::thread t1(depositos,3, 400);
    std::thread t2(saques,4,100);

    t1.join();
    t2.join();   
    std::cout << "Saldo final: " << saldo << std::endl; 

}
