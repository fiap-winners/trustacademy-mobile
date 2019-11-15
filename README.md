# trustacademy-mobile

Plataforma digital baseada em blockchain criada para assegurar todo o processo de coleta, geração e verificação de documentos acadêmicos.

Aplicativo destinado ao usuário/estudante. Esse MVP compreende o módulo para requisição de novos documentos e novas versões, e também a consulta dos documentos armazenados.

## Teste de funcionalidade

### Objetivo
O objetivo do teste é avaliar o processo de segurança de confirmação de identidade por meio de autenticação por imagem do solicitante baseado em **inteligência artificial**.

### Funcionalidade
Ao solicitar a emissão de um novo documento o aplicativo pedirá que o usuário forneça uma fotografia de si mesmo para comparar com a imagem armazenada no cadastro do usuário e assim autenticar a identidade do solicitante.

### Procedimentos
Para verificar a funcionalidade siga os passos:

1. Criar uma conta de usuário no app. Será solicitado uma fotografia para armazenar no cadastro;
2. Procure posicionar-se de frente para a câmera frontal do celular e enquadrar o rosto de forma que a imagem fique iluminada e o rosto esteja em evidência;
3. Após realizar o cadastro efetue o login com o usuário/senha cadastrados;
4. Será apresentada uma tela com os documentos já emitidos. Ao clicar em um documento será possível verificar suas versões;
5. Na lista de documentos emitidos, ao clicar no botão "Novo documento" será exibida uma tela com listas dos tipos de documentos disponíveis, departamentos da instituição de ensino e seus respectivos cursos;
6. Após escolher o documento relativo ao custo desejado selecione "Gravar";
7. O aplicativo solicitará que o usuário tire uma foto para verificar a autenticidade de sua identidade. Siga o procedimento do **passo 2**;
8. Envie a fotografia clicando no botão "Mandar foto";
9. Aguarde a verificação da autenticidade do solicitante;
10. Verificada a identidade positivamente o novo documento será emitido e aparecerá na tela de documentos. Se o documento já tiver sido emitido anteriormente, uma nova versão será adicionada;
11. Se identificada uma identidade diferente o documento não é emitido.

## Tecnologia
A verificação da autenticidade do solicitante é realizada utilizando o serviço **Amazon Rekognition Image** da AWS.
A imagem de referência, do cadastro do usuário, e a imagem do momento da requisição da emissão de novo documento são enviadas para o serviço de reconhecimento de imagem, que estabelece uma pontuação de similaridade entre elas. A identificação positiva é estabelecida obtendo uma pontuação igual ou superior a 70 pontos.

> Mais informações sobre o serviço podem ser obtidas [aqui](https://aws.amazon.com/pt/rekognition/image-features/).
