# Objetivo: PicPay Simplificado

O PicPay Simplificado é uma plataforma de pagamentos simplificada. Nela é possível depositar e realizar transferências de dinheiro entre usuários. Temos 2 tipos de usuários, os comuns e lojistas, ambos têm carteira com dinheiro e realizam transferências entre eles.

<i> É válido ressaltar que esse projeto foi feito baseando-se no desafio do Pic Pay para uma entrevista de emprego, no qual utilizei para melhorarar minhas habilidades dentro do ecossistema Spring Framework - o link está na descrição do repositório. </i>

# Requisitos

```javascript
POST /transfer
Content-Type: application/json

{
  "value": 100.0,
  "payer": 4,
  "payee": 15
}
```

* A seguir estão algumas regras de negócio que são importantes para o funcionamento do PicPay Simplificado:

* Para ambos tipos de usuário, precisamos do Nome Completo, CPF, e-mail e Senha. CPF/CNPJ e e-mails devem ser únicos no sistema. Sendo assim, seu sistema deve permitir apenas um cadastro com o mesmo CPF ou endereço de e-mail;

* Usuários podem enviar dinheiro (efetuar transferência) para lojistas e entre usuários;

* Lojistas só recebem transferências, não enviam dinheiro para ninguém;

* Validar se o usuário tem saldo antes da transferência;

* Antes de finalizar a transferência, deve-se consultar um serviço autorizador externo, use este mock https://util.devi.tools/api/v2/authorize para simular o serviço utilizando o verbo GET;

* A operação de transferência deve ser uma transação (ou seja, revertida em qualquer caso de inconsistência) e o dinheiro deve voltar para a carteira do usuário que envia;

* No recebimento de pagamento, o usuário ou lojista precisa receber notificação (envio de email, sms) enviada por um serviço de terceiro e eventualmente este serviço pode estar indisponível/instável. Use este mock https://util.devi.tools/api/v1/notify)) para simular o envio da notificação utilizando o verbo POST; <b> Atualemente esse serviço está indisponivel </b>

```java
@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

//        ResponseEntity<String> notificationApiResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);
//
//        if(!(notificationApiResponse.getStatusCode() == HttpStatus.OK)){
//            System.out.printf("erro ao enviar notificação");
//            throw new Exception("Serviço de notificação fora do ar");
//        }

        System.out.println("Notificação enviada para o usuário");

    }
```

