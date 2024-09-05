/* 
Nome: Erik Samuel Viana Hsu         RA: 10403109
Nome: Thiago Shihan Cardoso Toma    RA: 10400764
*/

#include <iostream> 

float saldo = 1000.00;

float depositos(int qtd, float valor){
    saldo = saldo + (qtd * valor);
    return saldo;
}

float saques(int qtd, float valor){
    saldo = saldo - (qtd * valor);
    return saldo;
}


int main(){

    //Exemplo realizando 3 depositos de 400 unidades monetarias
    std::cout << "Saldo depois dos depositos: " << depositos(3, 400) << std::endl;

     //Exemplo realizando 4 saques de 100 unidades monetarias
    std::cout << "Saldo depois dos saques: " << saques(4, 100) << std::endl;

    std::cout << "Saldo final: " << saldo << std::endl; 

}
