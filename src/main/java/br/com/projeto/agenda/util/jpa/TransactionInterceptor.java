package br.com.projeto.agenda.util.jpa;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/*
A implementação do interceptador de @Transactional consegue entender que só quem 
iniciou a transação é capaz de fazer commit ou rollback, por isso se a exceção 
chegar até o primeiro método (que abriu a transação), vai ser feito rollback de tudo.
*/

@Interceptor
@Transactional
public class TransactionInterceptor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        EntityTransaction trx = manager.getTransaction();
        boolean criador = false;

        try {
            if (!trx.isActive()) {
                // truque para fazer rollback no que já passou
                // (senão, um futuro commit, confirmaria até mesmo operações sem transação)
                trx.begin();
                trx.rollback();

                // agora sim inicia a transação
                trx.begin();

                criador = true;
            }

            //para aqui e continua executando método que chamou
            //caso o metodo chame outre com Transaction, não passara aqui novamante
            return context.proceed();
            
        } catch (Exception e) {            
            
            if (trx != null && criador) {
                trx.rollback();
            }

            throw e;
        } finally {
            if (trx != null && trx.isActive() && criador) {
                trx.commit();
            }
        }
    }
}
