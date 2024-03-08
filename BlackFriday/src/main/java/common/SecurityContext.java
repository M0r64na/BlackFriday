package common;

import javax.security.auth.Subject;

public final class SecurityContext {
    private static final ThreadLocal<Subject> subjectHolder = new ThreadLocal<>();

    public static void setSubject(Subject subject) {
        subjectHolder.set(subject);
    }

    public static Subject getSubject() {
        return subjectHolder.get();
    }

    public static void removeSubject() {
        subjectHolder.remove();
    }
}