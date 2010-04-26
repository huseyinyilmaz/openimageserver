package ois;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
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
		//1)Create A,B,C
		A a = new A();
		B b = new B();
		C c = new C();
		//2)Persist A
		
		pm.currentTransaction().begin();
		try {
			a.key = new KeyFactory.Builder(A.class.getSimpleName(),"A").getKey();
			pm.makePersistent(a);
            pm.currentTransaction().commit();
        } finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }
        log.info("id of a is " + a.key);
        
		
		//3)get A from DB and add B to its list
		
		try {
			pm.currentTransaction().begin();
			A newA = pm.getObjectById(A.class,"A");
			b.aKey = newA.key;
			newA.bList.add(b);
			b.key = new KeyFactory.Builder(newA.key).addChild(B.class.getSimpleName(), "B").getKey();
			//pm.makePersistent(newA);
			//pm.makePersistent(b);
			log.info("transaction active = "+pm.currentTransaction().isActive());
            pm.currentTransaction().commit();
        } finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }
		log.info("key of b " + b.key);
        
        //4) get B from DB and add C to its list
		/*
		pm.currentTransaction().begin();
		try {
			Key bKey  = new KeyFactory.Builder(A.class.getSimpleName(), a.key.getId()).addChild(B.class.getSimpleName(), b.key.getId()).getKey();
			B newB = pm.getObjectById(B.class,bKey);
			c.bKey = newB.key;
			newB.cList.add(c);
            pm.currentTransaction().commit();
        } finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }

		pm.currentTransaction().begin();
		try {
			String st = //KeyFactory.Builder(c.key).getString();
			KeyFactory.createKeyString(b.key, C.class.getSimpleName(), c.key.getId());
			
			Key cKey  = KeyFactory.stringToKey(st);
			
			C newC = pm.getObjectById(C.class,cKey);
			log.warning("key is = " + newC.key.toString());
			pm.currentTransaction().commit();
        } finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }

	*/	
	}
	
}

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

