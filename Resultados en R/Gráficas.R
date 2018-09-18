setwd("/home/david/Documents/IntelliJ Projects/Gradient/res10dim")

library(ggplot2)

sd_median = function(x){
  return(sqrt(sum((x-median(x))^2)/(length(x) - 1)))
}

funcs = c("Grienwank","Rosenbrock","Schwefel","Rastrigin")
datos = c("10dresultadosTSPLLineal.txt","10dresultadosTSPLSigmoide.txt","10dresultadosTSNewton.txt","10dresultadosAscensoColina.txt","10dAscensoColinaPL.txt","10dresultadosGradienteNewton.txt","10dresultadosGradienteDesc.txt")
met = c("TS con PL, Enf. Lineal","TS con PL, Enf. Sigmoide" ,"TS con PL, Enf. Newton", "Ascenso a la colina con dist. Gauss", "Ascenso a la colina con dist. PL","Descenso del Gradiente original - Newton","Descenso del Gradiente")
nomb = c("TSconPLEnfLineal","TSconPLEnfSigmoide" ,"TSconPLEnfNewton", "AscensoalacolinacondistGauss", "AscensoalacolinacondistPL", "DescGradNewt", "DescGrad")

a=1
j=2

data = read.table(paste0(funcs[a],datos[j]), header = T, sep = ";", dec = ".")

dataMatrix = as.matrix(data)
dataMatrix = dataMatrix[,-length(dataMatrix[1,])]

m = vector()
medias = vector()
minimos = vector()
maximos = vector()
x = vector()
desvest =0
desvest = vector()
i=1
while(i < nrow(dataMatrix)){
  medias[i] = mean(dataMatrix[i,])
  m[i] = median(dataMatrix[i,])
  minimos[i] = min(dataMatrix[i,])
  maximos[i] = max(dataMatrix[i,])
  if(i %% 50 == 0){
    desvest[i] = sd_median(dataMatrix[i,])  
  } else {
    desvest[i] = 0
  }
  x[i] = i
  i = i+1
}
desvest[1] = sd(dataMatrix[1,])
dataPlot = data.frame(m,minimos,maximos);

limits <- aes(ymax = m+desvest, ymin = m-desvest)

setEPS() 
postscript(file = paste0("graf",funcs[a],nomb[j],".eps"), height = 8/2.54, width = 16/2.54)

ggplot(data = dataPlot, aes(x=x, y = m)) + 
  geom_errorbar(limits, width = 0.25, colour="darkgoldenrod")  +
  geom_line(aes(y = m, colour="Mediana")) +
  geom_line(aes(y = minimos, colour="Mínimos")) + 
  geom_line(aes(y = maximos, colour="Máximos")) + xlab("Iteración") + ylab(paste0("fitness (",funcs[a],")")) + ggtitle(met[j]) +
  theme(panel.background = element_rect(fill='gray97', colour = 'white')) + 
  scale_color_manual(values = c(
    'Mediana' = 'black',
    'Mínimos' = 'blue',
    'Máximos' = 'red'))

dev.off()


