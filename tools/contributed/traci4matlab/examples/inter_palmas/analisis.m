clear all
close all
clc

import traci.constants

% Get the filename of the example scenario
[scenarioPath,~,~] = fileparts(which(mfilename));
% scenarioPath = [scenarioPath '\inter_palmas'];
cd(scenarioPath);

system(['sumo-gui -c ' './tls/inter_palmas.sumocfg --start&']);

% minimum green time for the vehicles
MIN_GREEN_TIME = 20;

% the first phase in tls plan. see 'pedcrossing.tll.xml'
VEHICLE_GREEN_PHASE = 0;

% the id of the traffic light (there is only one). This is identical to the
% id of the controlled intersection (by default)
TLSID = 'sanDiego';

% pedestrian edges at the controlled intersection
WALKINGAREAS = {':cluster_-427_339061698_w0', ':cluster_-427_339061698_w1'};
CROSSINGS = {':cluster_-427_339061698_c0', ':cluster_-427_339061698_c1'};

% execute the TraCI control loop"""
traci.init();

% Initialize the vectors where the emissions will be recorded
emissionsNoControl = zeros(1,3600);
emissionsControl = zeros(1,3600);
t = 50400:(54000-1);
tlsSwitchNoControl = 0;
tlsSwitchControl = 0;
tlsControl = 0;

% main loop. do something every simulation step until no more vehicles are
% loaded or running
for i = 1:3600
    traci.simulationStep();
    
    % Take the emissions for each time step
    emissionsNoControl(i) = traci.edge.getCO2Emission('-465');
    
    if traci.trafficlights.getPhase(TLSID) == VEHICLE_GREEN_PHASE + 1
        tlsSwitchNoControl = tlsSwitchNoControl + 1;
    end
    
    
end

traci.close()

system(['sumo-gui -c ' './tls_actuated/inter_palmas_actuated.sumocfg --start&']);

% execute the TraCI control loop"""
traci.init();

% track the duration for which the green phase of the vehicles has been active
greenTimeSoFar = 0;

% whether the pedestrian button has been pressed
activeRequest = 0;

% main loop. do something every simulation step until no more vehicles are
% loaded or running
for i = 1:3600
    traci.simulationStep();
    
    % Take the emissions for each time step
    emissionsControl(i) = traci.edge.getCO2Emission('-465');
    
    
    % decide wether there is a waiting pedestrian and switch if the green
    % phase for the vehicles exceeds its minimum duration
    if ~activeRequest
        activeRequest = checkWaitingPersons(WALKINGAREAS, CROSSINGS);
    end
    if traci.trafficlights.getPhase(TLSID) == VEHICLE_GREEN_PHASE
        greenTimeSoFar = greenTimeSoFar + 1;
        if greenTimeSoFar > MIN_GREEN_TIME
            % check whether someone has pushed the button
            
            if activeRequest
                % switch to the next phase
                traci.trafficlights.setPhase(TLSID, VEHICLE_GREEN_PHASE + 1);
                % reset state
                activeRequest = 0;
                greenTimeSoFar = 0;
                
                tlsSwitchControl = tlsSwitchControl + 1;
                
            end
        end
    end
end

traci.close()

totalEmissionsNoControl = sum(emissionsNoControl)/1000
totalEmissionsControl = sum(emissionsNoControl)/1000

plot(t(1:120),emissionsNoControl(1:120))
hold on;
plot(t(1:120),emissionsControl(1:120),'r')
title('CO2 emissions for Via Las Palmas');
xlabel('t')
ylabel('CO2 emissions [mg]')
legend('normal', 'with tls activated by pedestrian');