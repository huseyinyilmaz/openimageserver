package ois;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ois.model.PMF;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
@SuppressWarnings("serial")
public class OpenImageServerServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(OpenImageServerServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		A a = null;
		B b = null;
		C c = null;
		
		//1)If A does not exist persist A
		try{
			a = pm.getObjectById(A.class,"A");//check if A exist
		}catch(Exception e){
			try {
				pm.currentTransaction().begin();
				a = new A();
				a.key = new KeyFactory.Builder(A.class.getSimpleName(),"A").getKey();
				pm.makePersistent(a);
	            pm.currentTransaction().commit();
	            log.info("New a was created");
			} finally {
	        	if(pm.currentTransaction().isActive())
	        		pm.currentTransaction().rollback();
	        }
		}
        log.info("id of a is " + a.key);
		//2)get A from DB and add B to its list
		try {
			pm.currentTransaction().begin();
			A newA = pm.getObjectById(A.class,"A");
			b = new B();
			b.key = new KeyFactory.Builder(newA.key).addChild(B.class.getSimpleName(), "B").getKey();
			newA.bList.add(b);
			pm.currentTransaction().commit();
            log.info("New b was created");
		} finally {
			if(pm.currentTransaction().isActive())
				pm.currentTransaction().rollback();
		}
		log.info("Key of b " + b.key);
        //3) get B from DB and add C to its list
		pm.currentTransaction().begin();
		try {
			Key bKey  = new KeyFactory.Builder(A.class.getSimpleName(), "A").addChild(B.class.getSimpleName(), "B").getKey();
			B newB = pm.getObjectById(B.class,bKey);//Error we cannot retrieve B
			c = new C();
			c.key = new KeyFactory.Builder(newB.key).addChild(C.class.getSimpleName(), "C").getKey();
			newB.cList.add(c);
			pm.currentTransaction().commit();
            log.info("New c was created");
		} finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }
		//4) delete B and C from DB
        pm.currentTransaction().begin();
		try {
			C newC = pm.getObjectById(C.class,c.key);
			pm.deletePersistent(newC);
			B newB = pm.getObjectById(B.class,b.key);
			pm.deletePersistent(newB);
			pm.currentTransaction().commit();
            log.info("b and c was deleted");
		} finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }
	}
}

@PersistenceCapable
class A {
    @PrimaryKey
    @Persistent
    public Key key;
    @Persistent(mappedBy = "a") 
    @Element(dependent = "true") 
    public List<B> bList = new ArrayList<B>();
}	

@PersistenceCapable
class B {
    @PrimaryKey
    @Persistent
    public Key key;
    @Persistent(mappedBy = "b") 
    @Element(dependent = "true") 
    public List<C> cList = new ArrayList<C>();
    @Persistent
    public A a;
}

@PersistenceCapable
class C {
    @PrimaryKey
    @Persistent
    public Key key;
    @Persistent
    public B b;
}






/*
@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable = "true")
class A {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    public Key key;
    @Persistent(mappedBy = "a") 
    @Element(dependent = "true") 
    public List<B> bList = new LinkedList<B>();
}	

@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable = "true")
class B {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    public Key key;
    @Persistent(mappedBy = "b") 
    @Element(dependent = "true") 
    public List<C> cList = new LinkedList<C>();
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    public Key aKey;
    
    @Persistent
    public A a;
}

@PersistenceCapable(identityType = IdentityType.APPLICATION , detachable = "true")
class C {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    public Key key;
    
    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    public Key bKey;

    @Persistent
    public B b;

}

*/
