%% SUMO TRACI TEST
% THIS FILE AIMS TO TEST THE TRACI PROTOCOL FOR SUMO.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: traci_test.m 31 2016-09-28 15:16:56Z afacostag $

clear
close all
clc

%% MAIN
% HERE, WE START THE SUMO SIMULATOR AND INITIALIZE IT, ACCORDING TO
% THE EXAMPLE FOUND IN http://sumo-sim.org/userdoc/Tutorials/TraCI4Traffic_Lights.html

system('sumo -c ./inter_palmas/tls_actuated/inter_palmas_actuated.sumocfg&');

[traciVersion,sumoVersion] = traci.init();
traci.close();
fprintf('SUMO version: %s\nTraCI version: %d\n',sumoVersion,traciVersion);