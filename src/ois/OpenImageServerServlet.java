package ois;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.servlet.http.*;
import javax.jdo.annotations.IdentityType;

import ois.exceptions.PersistanceManagerException;
import ois.model.PMF;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class OpenImageServerServlet extends HttpServlet {
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
            pm.makePersistent(a);
            pm.currentTransaction().commit();
        } finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }
        //3)get A from DB and add B to its list
		pm.currentTransaction().begin();
		try {
			A newA = pm.getObjectById(A.class,a.key.getId());
			newA.bList.add(b);
            pm.currentTransaction().commit();
        } finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }
        //4) get B from DB and add C to its list
		pm.currentTransaction().begin();
		try {
			B newB = pm.getObjectById(B.class,b.key.getId());//we can not retrieve this object. why?
			newB.cList.add(c);
            pm.currentTransaction().commit();
        } finally {
        	if(pm.currentTransaction().isActive())
        		pm.currentTransaction().rollback();
        }
		
	}
}

@PersistenceCapable(identityType = IdentityType.APPLICATION)
class A {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    public Key key;
    @Persistent(mappedBy = "a") 
    @Element(dependent = "true") 
    public List<B> bList = new LinkedList<B>();
}	

@PersistenceCapable(identityType = IdentityType.APPLICATION)
class B {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    public Key key;
    @Persistent(mappedBy = "b") 
    @Element(dependent = "true") 
    public List<C> cList = new LinkedList<C>();
    @Persistent
    public A a;
}

@PersistenceCapable(identityType = IdentityType.APPLICATION)
class C {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    public Key key;
    @Persistent
    public B b;
}

