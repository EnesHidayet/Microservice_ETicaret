# KOMUT SETLERİ

    kubeclt - cli aracı 

## get komutu - kubernetes objelerinin bilgilerini çeker

    kubeclt get nodes -> çalışan asıl sanal PC miz
    kubeclt get pods -> nodes lar üzerinde çalıştırılan sanal küçük containerlar
    kubeclt get deployments
    kubectl get services
    kubectl get cronjob
    kubectl get secrets

    NOT!!!! bilgileri daha kapsamlı almak için -o wide eklemelisiniz.
    kubectl get nodes -o wide -> burada -o ve benzeri diğer komutları kubectl get --help yazarak açılımlarını ve işlevlerini görebiliriz.

## Pod nedir? Ne işe yarar.
    kubernetes içinde çalışabilir en küçük objemiz, oluşturmak için create komutunu kullanabiliriz ancak bu yöntem hantal ve kullanışsızdır. Bu nedenle daha kullanışlı olan deployment objesini kullanarak bunu yönetme yoluna gideriz.
    > kubectl run pod-authservice --image=eneshidayet/auth-service:v.0.1
    NOT!!!! çalışmayan yada gereksiz olduğunu düşündüğünüz podları silmek istiyorsanız,
    > kubectl delete pods [POD_NAME] yazmanız yeterlidir.

## Deploymnet Objesi

    podların durumlarını gözlemleyen, verilen emirler doğrultusunda podları oluşturan, güncelleme gerekli olduğunda bu güncellemeleri sisteme zarar vermeden ve aksatmadan
    güncellenmesini sağlayan k8s(k ubernete s) objesidir.

    kubectl ile bir deployment objesi create edebilirsiniz, ancak doğru yöntem bir yml dosyası kullanarak bunu yapmaktır.

## DİKKAT!!! bir yml dosyasını deploy etmek

    kubectl apply -f [YML_DOSYA_ADI]