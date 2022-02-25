#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <conio.h>
#include "park.h"


//ESTRUTURA DO CARRO 
typedef struct carpark{
    	char matricula[15];
		char proprietario[20];
		int number;
        time_t horaEntrada;
        time_t data;
        char codigo[1];
 }CARPARK;

//=======================================================================================================

int main (){ 

	//VARIAVÃ‰IS
	int nPiso, nFila, nColuna;
    float precoVerao;
	int tlugares;
	char checkMatricula[10];
    char searchMatricula[10];
    char stateMatricula[10];
	char nomeProp[20];
	int i,j,k;
	int nFilaes;
    float precoTotal;
    int checker = 0;
	char decision;
	char opcao;
	int contadorCarros;
	char adminNome[10];
	char adminPassword[10];

	//FICHEIRO .TXT 
	FILE *fp;
    FILE *fp2;
    fp2 = fopen("cars.txt", "w");
    fclose(fp2);
    

    				printf("REGISTE-SE");
    				printf("\nNome de administrador: ");
					scanf("%s", &adminNome);
					printf("\nPassword: ");
					scanf("%s", &adminPassword);
    			

	menu:
    	printf("+------------------------------------------------------------------------------------------+\n");
		printf("|                                                                                          |\n");
		printf("|                  SEJA BEM-VINDO AO NOSSO ESTACIONAMENTO AUTOMATICO                       |\n");
		printf("|                                                                                          |\n");
    	printf("+------------------------------------------------------------------------------------------+\n");
    	printf("\n\n                                DEFINA O SEU PARQUE                                     \n");
    	printf("\nInsira, por favor:\n\tO numero de nPisos que deseja que o seu parque tenha: "); 
    	scanf("%d", &nPiso);
    	
    	printf("\tO numero de filas, por nPiso, que deseja que o seu parque tenha: ");
    	scanf("%d", &nFila);
    	
		printf("\tO numero de colunas, por nPiso, que deseja que o seu parque tenha: ");
		scanf("%d", &nColuna);
		
    	printf("\tQual o preco por hora que deja estabelecer no seu parque para a altura do Verao? ");
    	scanf("%f", &precoVerao);
    	
		system("cls"); //comando que limpa a tela 


			
			//DEFINIÇÃO DO PARQUE
            CARPARK park[nPiso][nFila][nColuna];	
	        for( j = 0; j < nPiso; j++){
	            for ( i = 0; i < nFila; i++){
					for(k=0; k < nColuna; k++){
	                   strcpy(park[j][i][k].codigo, "0");

				   }
	            }   
	        }
	        sleep(2); //para dar tempo ao programa para correr o ciclo completo
			
            
        while (decision != 5){ 
            menu2:
            	system("cls");
	            printf("+----------------------------------+\n");
	            printf("|     PARQUE DE ESTACIONAMENTO     |\n");
	            printf("+----------------------------------+\n");

				fp = fopen("park.txt", "w");
            		for( j = 0; j <nPiso; j++){
               		 	printf("\n * * * * * No Piso %d * * * * *\n", j);
							fprintf(fp,"\n * * * * * No Piso %d * * * * *\n", j);
                			for ( i = 0; i < nFila; i++){
								for(k = 0; k < nColuna; k++){
                   			 		printf("[%s]", park[j][i][k].codigo);
									fprintf(fp,"[%s]", park[j][i][k].codigo);	
								} printf("\n");
								fprintf(fp,"\n");
                  		  	}
               			 	printf("\n");
							fprintf(fp,"\n");
           			 }
				fflush(stdin);
				fclose(fp);

		            printf("+----------------------------------+\n");
		            printf("|               MENU               |\n");
		            printf("+----------------------------------+\n");
            		printf("\n\t 1.Mostrar tabela de precos \n\t 2.Estacionar o seu veiculo \n\t 3.Ver os dados de um veiculo num determinado lugar do parque\n\t 4.Remover Veiculo\n\t 5.Alterar preco do parque \n\t 6.Sair\n ");
            		printf("\nInsira a sua opcao: ");
            		scanf(" %s",&decision);
					if(decision > '6' || decision < '1'){
						printf("Opcao invalida!");
						sleep(1.5);
						goto menu2;
					}
            
            switch(decision){
                case '1':
					tabPreco(precoVerao);
					goto menu2;

                case '2':
                	printf("\n");
	            	printf("+----------------------------------+\n");
	            	printf("|       INSIRA OS SEUS DADOS       |\n");
	            	printf("+----------------------------------+\n");
						
						insertData:	
					printf("Insira o seu Nome: ");
						scanf("%s", &nomeProp);
					printf("Indique a matricula do seu veiculo: ");
                        scanf("%s", &checkMatricula);
                        strupr(checkMatricula);

                        for(i=0; i < nPiso; i++){
                            for(j=0; j <nFila; j++){
								for(k=0; k < nColuna; k++){
                                	if(strcmp(park[i][j][k].matricula, checkMatricula) == 0){
                                	printf("\n");
									printf("      + -   -   -   -   -   -   -   -   -   -   +\n");
			                    	printf("      | A matricula ja existe! Tente Novamente! |\n");
									printf("      + -   -   -   -   -   -   -   -   -   -   +\n");
									goto insertData;
									}
                                }
                            }
                        }
				    sleep(1);
                    
					printf("\n");
                    printf("\n\t  ----------------------------");
           			printf("\n\t | MATRICULA LIDA COM SUCESSO |");
           			printf("\n\t  ----------------------------  \n");

                    //GUARDAR A VIATURA NO ARRAY 
                        for(i=0; i <= nPiso ; i++){
                        	checker = 0;
                            for(j=0; j <= nColuna; j++){
                            	checker = 0;
								for(k=0; k <= nFila; k++){
									checker = 0;
                                	if (strcmp(park[i][j][k].codigo, "0") == 0){
                                    		strcpy(park[i][j][k].codigo, "X");
                                    		strcpy(park[i][j][k].matricula, checkMatricula);
                                    		//DEFINICAO DA HORA DE ENTRADA
                                    		time_t ent = time(NULL);
                                    		park[i][j][k].horaEntrada = ent;
                                    		time_t printTime = time(NULL);
                                    		struct tm *time = localtime(&printTime);
  										  	//--
											strcpy(park[i][j][k].proprietario, nomeProp);
											park[i][j][k].number = contadorCarros + 1;
                                    		printf("\n\nEntrada da viatura %s as: %s",park[i][j][k].matricula, asctime(time));
											printf("Matricula: %s",park[i][j][k].matricula);
											printf("\nProprietario: %s \n", park[i][j][k].proprietario);
                                    		checker = 1;
											contadorCarros++;
                                    		break;
									}
									
                                }if(checker == 1)break;
                            }if(checker == 1)break;
                        }
                        sleep(1);
                        
                        
                        //FICHEIRO cars.TXT                      
                        fp2 = fopen("cars.txt","a");
								fprintf(fp2, "Carro N.: %d \n\n ", park[i][j][k].number);
                                fprintf(fp2,"\n\tMatricula: %s",park[i][j][k].matricula);
                                struct tm *t = localtime(&park[i][j][k].horaEntrada);
                                fprintf(fp2,"\n\tHora de entrada: %s",asctime(t));
								fprintf(fp2,"\tNome do Proprietario: %s", park[i][j][k].proprietario);
                                fprintf(fp2,"\n\tnPiso: %d ",i);
                                fprintf(fp2,"\n\tFila: %d ",j+1);
								fprintf(fp2,"\n\tcoluna: %d \n\n\n", k+1);
        						fflush(stdin);
								fclose(fp2);
                        		sleep(1);
						puts("Press any key to continue ... ");
                        getch();
                		goto menu2;
               

                case '3':
					printf("\n\tPara verificar os dados do seu veiculo, por favor insira a matricula: ");
                    scanf("%s", &stateMatricula);
                    strupr(stateMatricula);
                    int statePresence = 0;

                    for(i = 0; i < nPiso ; i++){
                        for(j = 0; j < nFila ; j++){
							for(k=0; k < nColuna; k++){
								if(strcmp(park[i][j][k].matricula, stateMatricula) == 0) statePresence = 1;
							}
                        }
                    }

                    //depois de percorrer o array, se nao o checkPresence nÃ£o tiver mudado de valor entao
                    if (statePresence == 0){
                    	printf("\n");
						printf("      + -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   - + \n");
                    	printf("      |      Infelizmente nao foi possivel localizar o seu Veiculo!   | \n");
						printf("      + -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   - + \n");
						puts("Press any key to continue ... ");
                        getch();
                    }else{
                        for(i = 0; i < nPiso ; i++){
                            for(j = 0; j < nFila ; j++){
                                for(k=0; k < nColuna; k++){
									if(strcmp(park[i][j][k].matricula, stateMatricula) == 0){
                                    	time_t saiState = time(NULL);
                                    	
						            	printf("\n");
					        			printf("+----------------------------------+\n");
					        			printf("|         DADOS DO VEICULO         |\n");
					        			printf("+----------------------------------+\n");
					        			
                                    	printf("\n\tMatricula: %s",park[i][j][k].matricula);
                                    	struct tm *time1 = localtime(&park[i][j][k].horaEntrada);
                                    	printf("\n\tHora de entrada: %s",asctime(time1) );
										printf("\n\tNome do Proprietario: %s", park[i][j][k].proprietario);
                                    	printf("\n\tPiso: %d ",i);
                                    	printf("\n\tFila: %d ",j+1);
										printf("\n\tColuna: %d \n\n\n", k+1);        
                                	}
								}
                            }
                        }
						puts("Press any key to continue ... ");
                        getch();
                    }
                    goto menu2;
					

                case '4':
					printf("\n");
            		printf("+----------------------------------+\n");
        			printf("|        REMOCAO DO VEICULO        |\n");
        			printf("+----------------------------------+\n");
                	printf("\n\tPara retirar o seu veiculo, por favor insira a matricula do seu veiculo: ");
                	scanf("%s", &searchMatricula);
                	strupr(searchMatricula);
                	
                	int checkPresence = 0;

                	for(i = 0; i < nPiso ; i++){
                   		for(j = 0; j < nFila ; j++){
							for(k=0; k < nColuna; k++){
                        		if(strcmp(park[i][j][k].matricula, searchMatricula) == 0) checkPresence = 1;
							}
                    	}
               	 	}
               	 	sleep(1);

                	//depois de percorrer o array, se nao o checkPresence nÃ£o tiver mudado de valor entao
                	if (checkPresence == 0){
                		printf("\n");
						printf("      + -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   - + \n");
                    	printf("      |      Infelizmente nao foi possivel localizar o seu Veiculo!   | \n");
						printf("      + -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   - + \n");
						puts("Press any key to continue ... ");
                        getch();
                	}else{
                    	for(i = 0; i < nPiso ; i++){
                        	for(j = 0; j < nFila ; j++){
								for(k=0; k < nColuna; k++){
                            		if(strcmp(park[i][j][k].matricula, searchMatricula) == 0){
                                		strcpy(park[i][j][k].codigo, "0");
                                		strcpy(park[i][j][k].proprietario, "");
                                		time_t sai = time(NULL);
                                		time_t tempo = time(NULL); 
										struct tm *time = localtime(&tempo);      
										printf("\n\tO veiculo com a matricula %s saiu do parque as %s", park[i][j][k].matricula, asctime(time));
										//preco por segundo * numero de segundos que o carro la esteve
										printf("\n\t  -------------------------");
                                		printf("\n\t |   VALOR A PAGAR: %.2f    |", ((precoVerao/3600)*(sai - park[i][j][k].horaEntrada)));
                                		printf("\n\t  -------------------------  \n" );
                                		park[i][j][k].horaEntrada = 0;
                                		strcpy(park[i][j][k].matricula, "");                  
                            		}
								}
                        	}
                    	}
                    	sleep(0.5);
						puts("Press any key to continue ... ");
                        getch();
                	}
                goto menu2;
                
				case '5':
				
				if((autenticar(*adminNome, *adminPassword)) == 1){ goto menu2;
				}else{
										
				alteracao:	
					printf("\n");				
					printf("+----------------------------------+\n");
            		printf("|        ALTERACAO DO PRECO        |\n");
            		printf("+----------------------------------+\n");
					printf("\n\tInsira um novo preco para a epoca Primavera/Verao: ");
					scanf("%f", &precoVerao);
					sleep(1);
			}
					goto menu2; 

				case '6':
				   encerrar(opcao);
				   goto menu2;
            }
					
        }
}


