%% SUMO TRACI TEST
% THIS FILE AIMS TO TEST THE TRACI PROTOCOL FOR SUMO.

%   Copyright 2013 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: traci_test.m 4 2014-03-03 15:18:05Z afacostag $

clear all
close all
clc

%% MAIN
% HERE, WE START THE SUMO SIMULATOR AND INITIALIZE IT, ACCORDING TO
% THE EXAMPLE FOUND IN http://sumo-sim.org/userdoc/Tutorials/TraCI4Traffic_Lights.html

% Tutorial in docs
if exist([getenv('SUMO_HOME') '\docs\tutorial\traci_tls\data\cross.sumocfg'],...
        'file')==2
    system(['sumo-gui -c ' getenv('SUMO_HOME')...
        '\docs\tutorial\traci_tls\data\cross.sumocfg --start&']);
% Tutorial in tests
elseif exist([getenv('SUMO_HOME') '\tests\complex\tutorial\traci_tls\data\cross.sumocfg'],...
        'file')==2
    system(['sumo-gui -c ' getenv('SUMO_HOME')...
        '\tests\complex\tutorial\traci_tls\data\cross.sumocfg --start&']);
end

[traciVersion,sumoVersion] = traci.init();
traci.close();
fprintf('SUMO version: %s\nTraCI version: %d\n',sumoVersion,traciVersion);