# YaleB_SVM


make directory to src

//train the model
java -classpath libsvm.jar svm_train ./trainingdata

//predict with the model
java -classpath libsvm.jar svm_train newdata trainingdata.model

//tuning the parameters for linear kernel
java -classpath libsvm.jar svm_train -s 0 -t 0 -c 1 -v 10 newdata newdata.model

//tuning the parameters for rbf kernel
java -classpath libsvm.jar svm_train -s 0 -t 0 -c 1 -g 0.1 -v 10 newdata newdata.model
