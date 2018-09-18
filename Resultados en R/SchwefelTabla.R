setwd("/home/david/Documents/IntelliJ Projects/Gradient/res2dim")

Metodo = vector();
Mean = vector();
SD = vector();
Median = vector();
SD_Median=vector();



func = "Schwefel";

sd_median = function(x){
  return(sqrt(sum((x-median(x))^2)/(length(x) - 1)))
}

data = read.table(paste0(func,"2dresultadosGradienteNewton.txt"), header = T, sep = ";", dec = ".")

Metodo = append(Metodo,"Descenso del gradiente (Newton)")
data = as.matrix(data)
data = data[nrow(data),-ncol(data)]
Mean = append(Mean, mean(data[-which(is.na(data))]), after = length(Mean))
SD = append(SD, sd(data[-which(is.na(data))]), after = length(SD))
Median = append(Median, median(data[-which(is.na(data))]), after = length(Median))
SD_Median = append(SD_Median, sd_median(data[-which(is.na(data))]), after = length(Mean))

data = read.table(paste0(func,"2dresultadosGradienteDesc.txt"), header = T, sep = ";", dec = ".")

Metodo = append(Metodo,"Descenso del gradiente")
data = as.matrix(data)
data = data[nrow(data),-ncol(data)]
Mean = append(Mean, mean(data[-which(is.na(data))]), after = length(Mean))
SD = append(SD, sd(data[-which(is.na(data))]), after = length(SD))
Median = append(Median, median(data[-which(is.na(data))]), after = length(Median))
SD_Median = append(SD_Median, sd_median(data[-which(is.na(data))]), after = length(Mean))

data = read.table(paste0(func,"2dresultadosGradienteDescMomentum.txt"), header = T, sep = ";", dec = ".")

Metodo = append(Metodo,"Descenso del gradiente Momentum")
data = as.matrix(data)
data = data[nrow(data),-ncol(data)]
Mean = append(Mean, mean(data[-which(is.na(data))]), after = length(Mean))
SD = append(SD, sd(data[-which(is.na(data))]), after = length(SD))
Median = append(Median, median(data[-which(is.na(data))]), after = length(Median))
SD_Median = append(SD_Median, sd_median(data[-which(is.na(data))]), after = length(Mean))

data = read.table(paste0(func,"2dresultadosAscensoColina.txt"), header = T, sep = ";", dec = ".")

Metodo = append(Metodo,"Ascenso a la colina")
data = as.matrix(data)
data = data[nrow(data),-ncol(data)]
Mean = append(Mean, mean(data[-which(is.na(data))]), after = length(Mean))
SD = append(SD, sd(data[-which(is.na(data))]), after = length(SD))
Median = append(Median, median(data[-which(is.na(data))]), after = length(Median))
SD_Median = append(SD_Median, sd_median(data[-which(is.na(data))]), after = length(Mean))


data = read.table(paste0(func,"2dAscensoColinaPL.txt"), header = T, sep = ";", dec = ".")

Metodo = append(Metodo,"Ascenso a la colina PL")
data = as.matrix(data)
data = data[nrow(data),-ncol(data)]
Mean = append(Mean, mean(data[-which(is.na(data))]), after = length(Mean))
SD = append(SD, sd(data[-which(is.na(data))]), after = length(SD))
Median = append(Median, median(data[-which(is.na(data))]), after = length(Median))
SD_Median = append(SD_Median, sd_median(data[-which(is.na(data))]), after = length(Mean))



data = read.table(paste0(func,"2dresultadosTSLineal.txt"), header = T, sep = ";", dec = ".")

Metodo = append(Metodo,"Templado Simulado Enf Lineal")
data = as.matrix(data)
data = data[nrow(data),-ncol(data)]
Mean = append(Mean, mean(data[-which(is.na(data))]), after = length(Mean))
SD = append(SD, sd(data[-which(is.na(data))]), after = length(SD))
Median = append(Median, median(data[-which(is.na(data))]), after = length(Median))
SD_Median = append(SD_Median, sd_median(data[-which(is.na(data))]), after = length(Mean))


data = read.table(paste0(func,"2dresultadosTSPLLineal.txt"), header = T, sep = ";", dec = ".")

Metodo = append(Metodo,"Templado Simulado Enf Lineal PL")
data = as.matrix(data)
data = data[nrow(data),-ncol(data)]
Mean = append(Mean, mean(data[-which(is.na(data))]), after = length(Mean))
SD = append(SD, sd(data[-which(is.na(data))]), after = length(SD))
Median = append(Median, median(data[-which(is.na(data))]), after = length(Median))
SD_Median = append(SD_Median, sd_median(data[-which(is.na(data))]), after = length(Mean))


data = read.table(paste0(func,"2dresultadosTSNewton.txt"), header = T, sep = ";", dec = ".")

Metodo = append(Metodo,"Templado Simulado Enf Newton")
data = as.matrix(data)
data = data[nrow(data),-ncol(data)]
Mean = append(Mean, mean(data[-which(is.na(data))]), after = length(Mean))
SD = append(SD, sd(data[-which(is.na(data))]), after = length(SD))
Median = append(Median, median(data[-which(is.na(data))]), after = length(Median))
SD_Median = append(SD_Median, sd_median(data[-which(is.na(data))]), after = length(Mean))



data = read.table(paste0(func,"2dresultadosTSPLNewton.txt"), header = T, sep = ";", dec = ".")

Metodo = append(Metodo,"Templado Simulado Enf Newton PL")
data = as.matrix(data)
data = data[nrow(data),-ncol(data)]
Mean = append(Mean, mean(data[-which(is.na(data))]), after = length(Mean))
SD = append(SD, sd(data[-which(is.na(data))]), after = length(SD))
Median = append(Median, median(data[-which(is.na(data))]), after = length(Median))
SD_Median = append(SD_Median, sd_median(data[-which(is.na(data))]), after = length(Mean))



data = read.table(paste0(func,"2dresultadosTSSigmoide.txt"), header = T, sep = ";", dec = ".")

Metodo = append(Metodo,"Templado Simulado Enf Sigmoide")
data = as.matrix(data)
data = data[nrow(data),-ncol(data)]
Mean = append(Mean, mean(data[-which(is.na(data))]), after = length(Mean))
SD = append(SD, sd(data[-which(is.na(data))]), after = length(SD))
Median = append(Median, median(data[-which(is.na(data))]), after = length(Median))
SD_Median = append(SD_Median, sd_median(data[-which(is.na(data))]), after = length(Mean))

data = read.table(paste0(func,"2dresultadosTSPLSigmoide.txt"), header = T, sep = ";", dec = ".")

Metodo = append(Metodo,"Templado Simulado Enf Sigmoide PL")
data = as.matrix(data)
data = data[nrow(data),-ncol(data)]
Mean = append(Mean, mean(data[-which(is.na(data))]), after = length(Mean))
SD = append(SD, sd(data[-which(is.na(data))]), after = length(SD))
Median = append(Median, median(data[-which(is.na(data))]), after = length(Median))
SD_Median = append(SD_Median, sd_median(data[-which(is.na(data))]), after = length(Mean))



schwefel = data.frame(Metodo,Mean,SD,Median,SD_Median)