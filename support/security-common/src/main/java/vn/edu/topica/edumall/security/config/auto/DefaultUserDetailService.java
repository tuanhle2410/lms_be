//package vn.edu.topica.edumall.security.config.auto;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Query;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import vn.edu.topica.edumall.data.model.User;
//import vn.edu.topica.edumall.security.core.model.UserPrincipal;
//
///**
// * @author trungnt9
// *
// */
//public class DefaultUserDetailService implements UserDetailsService {
//
//	private EntityManagerFactory emf;
//
//	public DefaultUserDetailService(EntityManagerFactory emf) {
//		this.emf = emf;
//	}
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		try {
//			EntityManager em = emf.createEntityManager();
//			Query query = em.createQuery(
//					"from User a left join fetch a.listRole b where a.username = :username or a.email = :email");
//			query.setParameter("username", username);
//			query.setParameter("email", username);
//			User user = (User) query.getSingleResult();
//			if (user != null) {
//				return UserPrincipal.create(user);
//			}
//		} catch (Exception e) {
//			throw new UsernameNotFoundException("Error when loading user via user name");
//		}
//		return null;
//	}
//
//}
