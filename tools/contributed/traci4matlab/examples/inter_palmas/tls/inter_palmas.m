clear all
close all
clc

import traci.constants

% Get the filename of the example scenario
[scenarioPath,~,~] = fileparts(which(mfilename));
% scenarioPath = [scenarioPath '\inter_palmas'];
cd(scenarioPath);

system(['sumo-gui -c ' './inter_palmas.sumocfg&']);

% the first phase in tls plan. see 'pedcrossing.tll.xml'
VEHICLE_GREEN_PHASE = 0;

% the id of the traffic light (there is only one). This is identical to the
% id of the controlled intersection (by default)
TLSID = 'sanDiego';

% execute the TraCI control loop"""
traci.init();

% Initialize the vectors where the emissions will be recorded
emissionsEW = zeros(1,3600);
t = 50400:(54000-1);
tlsSwitch = 0;

% main loop. do something every simulation step until no more vehicles are
% loaded or running
for i = 1:3600
    traci.simulationStep();
    
    % Take the emissions for each time step
    emissionsEW(i) = traci.edge.getCO2Emission('-465');
    
    if traci.trafficlights.getPhase(TLSID) == VEHICLE_GREEN_PHASE + 1
        tlsSwitch = tlsSwitch + 1;
    end
    
    
end

traci.close()

totalEmissionsEWg = sum(emissionsEW)/1000

plot(t,emissionsWE)
title('CO2 emissions for Via Las Palmas');
xlabel('t')
ylabel('CO2 emissions [mg]')