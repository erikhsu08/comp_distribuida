/* 
Nome: Erik Samuel Viana Hsu         RA: 10403109
Nome: Thiago Shihan Cardoso Toma    RA: 10400764
*/

#include <iostream> 
#include <thread>

float saldo = 2000.00;

float depositos(int qtd, float valor){
    saldo = saldo + (qtd * valor);
    return saldo;
}

float saques(int qtd, float valor){
    saldo = saldo - (qtd * valor);
    return saldo;
}


int main(){

    std::thread t1(depositos,3, 400);
    std::thread t2(saques,4,100);


   std::cout << "Saldo final: " << saldo << std::endl; 


}
