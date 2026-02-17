# 🔧 Résolution des Problèmes de Dépendances Maven

## ❌ Problème : IntelliJ IDEA ne reconnaît pas les dépendances

Si IntelliJ IDEA affiche des erreurs sur les imports Spring Boot (annotations rouges), suivez ces étapes :

---

## ✅ Solution 1 : Recharger le Projet Maven (Rapide)

### Dans IntelliJ IDEA :

1. **Ouvrir la fenêtre Maven :**
   - Cliquez sur `View` → `Tool Windows` → `Maven`
   - Ou appuyez sur `Ctrl + Shift + O` puis tapez "Maven"

2. **Recharger le projet :**
   - Dans la fenêtre Maven, cliquez sur l'icône **🔄 Reload All Maven Projects**
   - Ou faites un clic droit sur le projet → `Maven` → `Reload Project`

3. **Attendez** que Maven télécharge toutes les dépendances

---

## ✅ Solution 2 : Invalidate Caches (Moyen)

### Dans IntelliJ IDEA :

1. **Menu :**
   - `File` → `Invalidate Caches...`

2. **Options :**
   - ✅ Cochez `Clear file system cache and Local History`
   - ✅ Cochez `Clear downloaded shared indexes`
   - ✅ Cochez `Clear VCS Log caches and indexes`

3. **Action :**
   - Cliquez sur `Invalidate and Restart`

4. **Attendre** que l'IDE redémarre et ré-indexe le projet

---

## ✅ Solution 3 : Réimporter Complètement (Complet)

### Étape 1 : Fermer le projet dans IntelliJ

1. `File` → `Close Project`

### Étape 2 : Nettoyer les fichiers IDE

```powershell
cd C:\Users\haelamri\Documents\projets\CABINET_MEDICAL\backend
Remove-Item -Recurse -Force .idea
Remove-Item -Force *.iml
```

### Étape 3 : Nettoyer Maven

```powershell
mvn clean
mvn dependency:purge-local-repository
```

### Étape 4 : Réinstaller les dépendances

```powershell
mvn clean install -U
```

### Étape 5 : Rouvrir le projet

1. Dans IntelliJ : `File` → `Open`
2. Sélectionnez le dossier `backend`
3. Attendez que Maven importe le projet

---

## ✅ Solution 4 : Utiliser le Script Automatique

Nous avons créé un script qui fait tout automatiquement :

```powershell
cd C:\Users\haelamri\Documents\projets\CABINET_MEDICAL\backend
.\fix-maven.bat
```

Puis dans IntelliJ :
- `File` → `Invalidate Caches` → `Invalidate and Restart`

---

## 🔍 Vérifier que Maven fonctionne

### Test 1 : Compilation en ligne de commande

```powershell
cd C:\Users\haelamri\Documents\projets\CABINET_MEDICAL\backend
mvn clean compile
```

**Si ça compile** → Le problème vient de l'IDE, pas de Maven

### Test 2 : Voir les dépendances

```powershell
mvn dependency:tree
```

**Vous devriez voir :**
- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `postgresql`
- `liquibase-core`
- etc.

---

## 📋 Checklist de Diagnostic

### ✅ Vérifier Java

```powershell
java -version
```
**Doit afficher :** Java 17 ou supérieur

### ✅ Vérifier Maven

```powershell
mvn -version
```
**Doit afficher :** Apache Maven 3.x

### ✅ Vérifier le pom.xml

Le fichier `backend/pom.xml` doit contenir :
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.2</version>
</parent>
```

### ✅ Vérifier les dépendances dans pom.xml

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

---

## 🎯 Configuration IntelliJ IDEA

### Vérifier le SDK Java

1. `File` → `Project Structure` (Ctrl+Alt+Shift+S)
2. `Project Settings` → `Project`
3. **Project SDK** doit être : `17` ou `corretto-17`
4. **Language level** doit être : `17 - Sealed types, always-strict floating-point semantics`

### Vérifier Maven dans IntelliJ

1. `File` → `Settings` (Ctrl+Alt+S)
2. `Build, Execution, Deployment` → `Build Tools` → `Maven`
3. **Maven home path** doit pointer vers votre installation Maven
4. **User settings file** doit pointer vers `~/.m2/settings.xml`

### Activer l'auto-import Maven

1. `File` → `Settings`
2. `Build, Execution, Deployment` → `Build Tools` → `Maven` → `Importing`
3. ✅ Cochez `Import Maven projects automatically`
4. ✅ Cochez `Automatically download sources`
5. ✅ Cochez `Automatically download documentation`

---

## 🚨 Erreurs Courantes

### Erreur : "Cannot resolve symbol 'SpringBootApplication'"

**Cause :** Maven n'a pas téléchargé `spring-boot-starter`

**Solution :**
```powershell
mvn dependency:resolve -Dartifact=org.springframework.boot:spring-boot-starter:3.2.2
mvn clean install
```

### Erreur : "Package org.springframework.boot does not exist"

**Cause :** Le classpath n'est pas configuré

**Solution :**
1. Dans IntelliJ : Maven panel → `Reload All Maven Projects`
2. `Build` → `Rebuild Project`

### Erreur : "Java: package javax.persistence does not exist"

**Cause :** Spring Boot 3 utilise `jakarta.persistence` au lieu de `javax.persistence`

**Solution :** Vérifiez que vos entités utilisent :
```java
import jakarta.persistence.*;  // ✅ Correct pour Spring Boot 3
```

---

## 📞 Si Rien ne Fonctionne

### Option 1 : Supprimer le cache Maven local

```powershell
Remove-Item -Recurse -Force $HOME\.m2\repository\org\springframework
mvn clean install
```

### Option 2 : Créer un nouveau projet test

Pour vérifier que Maven/Spring Boot fonctionne :

```powershell
mvn archetype:generate `
  -DgroupId=com.test `
  -DartifactId=test-spring `
  -DarchetypeArtifactId=maven-archetype-quickstart `
  -DinteractiveMode=false
```

### Option 3 : Vérifier les proxies/firewall

Si vous êtes derrière un proxy d'entreprise, configurez `~/.m2/settings.xml` :

```xml
<settings>
  <proxies>
    <proxy>
      <id>proxy</id>
      <active>true</active>
      <protocol>http</protocol>
      <host>proxy.company.com</host>
      <port>8080</port>
    </proxy>
  </proxies>
</settings>
```

---

## ✅ Vérification Finale

Après avoir appliqué les solutions, testez :

```powershell
cd C:\Users\haelamri\Documents\projets\CABINET_MEDICAL\backend
mvn clean compile
mvn test
mvn spring-boot:run
```

**Si tout fonctionne :**
- ✅ La compilation passe
- ✅ Les tests passent
- ✅ L'application démarre sur http://localhost:8080

**Dans IntelliJ IDEA :**
- ✅ Plus d'imports rouges
- ✅ L'autocomplétion fonctionne
- ✅ Vous pouvez lancer l'application avec le bouton ▶️

---

## 📚 Ressources

- [Maven Getting Started](https://maven.apache.org/guides/getting-started/)
- [Spring Boot Maven Plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/)
- [IntelliJ IDEA Maven Support](https://www.jetbrains.com/help/idea/maven-support.html)

---

**Créé le :** 2026-02-17  
**Objectif :** Résoudre les problèmes de dépendances Maven dans IntelliJ IDEA

