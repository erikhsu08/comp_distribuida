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

// Função para ler os dados de uma pessoa a partir do arquivo
void lerPessoa(FILE *arquivo, struct Pessoa *pessoa) {
    if (fgets(pessoa->nome, sizeof(pessoa->nome), arquivo)) {
        // Remove a nova linha do nome
        pessoa->nome[strcspn(pessoa->nome, "\n")] = '\0';

        // Lê a idade e altura
        fscanf(arquivo, "%d", &pessoa->idade);
        fscanf(arquivo, "%f", &pessoa->altura);
        fgetc(arquivo); // Consumir o caractere de nova linha restante
    }
}

// Função de comparação para qsort
int compararAltura(const void *a, const void *b) {
    struct Pessoa *pessoaA = (struct Pessoa *)a;
    struct Pessoa *pessoaB = (struct Pessoa *)b;

    if (pessoaA->altura < pessoaB->altura) {
        return -1;
    } else if (pessoaA->altura > pessoaB->altura) {
        return 1;
    } else {
        return 0;
    }
}

// Função para ordenar o vetor de pessoas por altura
void ordenarPessoas(struct Pessoa pessoas[], int cont) {
    qsort(pessoas, cont, sizeof(struct Pessoa), compararAltura);
}

// Função para imprimir os dados das pessoas no arquivo de saída
void imprimirPessoas(FILE *arquivo, struct Pessoa pessoas[], int cont) {
    for (int i = 0; i < cont; i++) {
        fprintf(arquivo, "%s\n%d\n%.2f\n", pessoas[i].nome, pessoas[i].idade, pessoas[i].altura);
    }
}

int main() {
    FILE *arquivo;
    struct Pessoa pessoas[100];
    int cont = 0;

    // Abrir o arquivo de entrada
    arquivo = fopen("entrada.txt", "r");
    if (arquivo == NULL) {
        printf("Erro ao abrir o arquivo");
        return 1;
    }

    // Ler os dados do arquivo e armazenar no vetor de pessoas
    while (!feof(arquivo) && cont < 100) {
        lerPessoa(arquivo, &pessoas[cont]);
        cont++;
    }
    fclose(arquivo);

    // Ordenar os dados por altura
    ordenarPessoas(pessoas, cont);

    // Abrir o arquivo de saída
    arquivo = fopen("saida.txt", "w");
    if (arquivo == NULL) {
        printf("Erro ao abrir o arquivo de saída");
        return 1;
    }

    // Imprimir os dados ordenados no arquivo de saída
    imprimirPessoas(arquivo, pessoas, cont);
    fclose(arquivo);

    printf("Dados ordenados por altura: Verifique o arquivo de saída");

    return 0;
}
