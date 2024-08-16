/*
NOME: Erik Samuel Viana Hsu     RA: 10403109
NOME: Mateus Kenzo Iochimoto    RA: 10400995
 */

 
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


struct Pessoa {
    char nome[50];
    int idade;
    float altura;
};

int main(){
    FILE *arquivo;
     //Criar o vetor
    struct Pessoa pessoas[100];
    int cont = 0;

    arquivo = fopen("entrada.txt", "r");

    if (arquivo == NULL){
        printf("Erro ao abrir o arquivo");
        return 1;
    }

    //Le o arquivo e armazena os dados no vetor
    while(fgets(pessoas[cont].nome, sizeof(pessoas[cont].nome), arquivo)){
        //Remove a nova linha do nome
        pessoas[cont].nome[strcspn(pessoas[cont].nome, "\n")] = '\0';

        //le a idade e altura
        fscanf(arquivo, "%d", &pessoas[cont].idade);
        fscanf(arquivo, "%f", &pessoas[cont].altura);
        fgetc(arquivo);

        cont++;
    }

    int compararAltura(const void *a, const void *b){
        struct Pessoa *pessoaA = (struct Pessoa *)a;
        struct Pessoa *pessoaB = (struct Pessoa *)b;

        if(pessoaA->altura < pessoaB->altura){
            return -1;
        }else if(pessoaA->altura > pessoaB->altura){
            return 1;
        }else{
            return 0;
        }
    }

    //Fechar o arquivo
    fclose(arquivo);

    qsort(pessoas, cont, sizeof(struct Pessoa), compararAltura);

    //Abrir arquivo de saida
    arquivo = fopen("saida.txt", "w");
    if (arquivo == NULL){
        printf("Erro ao abrir arquivo de saida");
        return 1;
    }

    //Escrever os dados no arquivo de saida

    for(int i = 0; i < cont; i++){
        fprintf(arquivo, "%s\n%d\n%.2f\n", pessoas[i].nome, pessoas[i].idade, pessoas[i].altura);
    }

    fclose(arquivo);

    printf("Dados ordenados por altura: Verifique o arquivo de saída");

    return 0;

}
