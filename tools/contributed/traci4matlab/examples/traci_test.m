%% SUMO TRACI TEST
% THIS FILE AIMS TO TEST THE TRACI PROTOCOL FOR SUMO.

%   Copyright 2013 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

clear all
close all
clc

%% MAIN
% HERE, WE START THE SUMO SIMULATOR AND INITIALIZE IT, ACCORDING TO
% THE EXAMPLE FOUND IN http://sumo-sim.org/userdoc/Tutorials/TraCI4Traffic_Lights.html
system(['sumo-gui -c ' getenv('SUMO_HOME') '\docs\tutorial\traci_tls\data\cross.sumocfg&']);
[traciVersion sumoVersion] = traci.init();
traci.close();
fprintf('SUMO version: %s\nTraCI version: %d\n',sumoVersion,traciVersion);