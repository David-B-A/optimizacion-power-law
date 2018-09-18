library(ggplot2)

sigm = array()
lin = array()
expo = array()
x = array()
N=2000
r=0.2
for(t in c(1:2000)){
  x=append(x,t,length(x))
  lin = append(lin, 1 - (1/2000)*t,length(lin))
  expo = append(expo,exp((-t * log(N) / 2000)),length(expo))
  sigm = append(sigm,(1/(1+exp((t-N/2)/(N*r)))  - 1/(1+exp((N/2)/(N*r)))  )*(( ( 1 - 1/N ) /(1/(1+exp((-1000)/(N*r))) - 1/(1+exp((N/2)/(N*r))) ))) + 1/N,length(sigm))
}
x = x[-1]
lin = lin[-1]
expo = expo[-1]
sigm = sigm[-1]
dataPlot = data.frame(x,lin,expo,sigm)

ggplot(data = dataPlot, aes(x=x, y = lin)) + 
  geom_line(aes(y = lin, color="Lineal")) +
  geom_line(aes(y = sigm, colour="Sigmoide")) + 
  geom_line(aes(y = expo, colour="Exponencial")) + xlab("Iteración") + ylab("Temperatura")+ ggtitle("Enfriamiento") +
  theme(panel.background = element_rect(fill='gray97', colour = 'white')) + 
  scale_color_manual(values = c(
    'Lineal' = 'black',
    'Sigmoide' = 'blue',
    'Exponencial' = 'red'))
