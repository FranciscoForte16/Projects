#ifndef PARK_H
#define PARK_H

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <conio.h>



//=======================================================================================================================================================================================================
void tabPreco (float precoVerao){
	 float precoInverno = precoVerao + 0.5;
            system("cls"); // comando que limpa a tela
 		    printf("+-----------------+-------------------------------------------------------------------------------------------------------------+\n");
            printf("|                                              .::Tabela de preços::.                                                          |\n");
            printf("+-----------------+-------------------------------------------------------------------------------------------------------------+\n");
            printf("|                 | Janeiro  | Fevereiro | Abril  | Maio  |  Junho | Julho  | Agosto | Setembro | Outubro | Novembro | Dezembro |\n");                
            printf("| Primavera/Verao |   %.2f   |   %.2f    |  %.2f  |  %.2f |  %.2f  |  %.2f  |  %.2f  |  %.2f    |   %.2f  |    %.2f  |    %.2f  |\n", precoVerao,precoVerao,precoVerao,precoVerao,precoVerao,precoVerao,precoVerao,precoVerao,precoVerao,precoVerao,precoVerao);
            printf("| Outono/Inverno  |  %.2f    |   %.2f    |  %.2f  |  %.2f |  %.2f  |  %.2f  |  %.2f  |   %.2f   |   %.2f  |    %.2f  |    %.2f  |\n", precoInverno,precoInverno,precoInverno,precoInverno,precoInverno,precoInverno,precoInverno,precoInverno,precoInverno,precoInverno,precoInverno);
            printf("+-----------------+-------------------------------------------------------------------------------------------------------------+\n");
            
            puts("Press any key to continue ... ");
            getch();
}
//=============================================================================================================
int encerrar(char opcao){
        printf("Tem a certeza que pretende encerrar o programa (S)/(N) ?");
        confirmacao:
            printf("\n");
            scanf("%s",&opcao);
    	    if(opcao=='S' || opcao=='s'){
                exit(1);
            }else if(opcao=='n' || opcao=='N'){
                return 1;
            }else{
                printf("Opção inválida!");
                goto confirmacao;
            }
}
//=============================================================================================================
int autenticar(char utilizador, char password){
	char passwordInsert[10];
	char decisao;
	
		autenticar:
        printf("\nPassword: ");
        scanf("%s", &passwordInsert);
        
        if(strcmp(passwordInsert, "admin") == 0 ){
        	return 0;
		}else{
			printf("Password incorreta!");
			printf("\nDeseja tentar novamente (S / N)?");
			scanf("%s", &decisao);
			if(decisao == 's' || decisao == 'S'){
				goto autenticar;
			}else{
				return 1;
			}
		}
}
//=============================================================================================================


#endif
