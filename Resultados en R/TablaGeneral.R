gwm1 = paste0(round(grienwank$Mean,2),"+-",round(grienwank$SD,2))
gwm2 = paste0(round(grienwank$Median,2),"+-",round(grienwank$SD_Median,2))

ras1 = paste0(round(rastrigin$Mean,2),"+-",round(rastrigin$SD,2))
ras2 = paste0(round(rastrigin$Median,2),"+-",round(rastrigin$SD_Median,2))

sch1 = paste0(round(schwefel$Mean,2),"+-",round(schwefel$SD,2))
sch2 = paste0(round(schwefel$Median,2),"+-",round(schwefel$SD_Median,2))

ros1 = paste0(round(rosenbrock$Mean,2),"+-",round(rosenbrock$SD,2))
ros2 = paste0(round(rosenbrock$Median,2),"+-",round(rosenbrock$SD_Median,2))

x = data.frame(ras1,ras2,sch1,sch2,ros1,ros2,gwm1,gwm2)
