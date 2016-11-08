function responses = simulationStep(timeStep)
%simulationStep Perform a simulation step in the SUMO server.
%
%   simulationStep() Perform a simulation step in the SUMO server with
%   a time step of one second.
%
%   simulationStep(STEP) Perform a simulation step in the SUMO server with
%   a time step STEP.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: simulationStep.m 31 2016-09-28 15:16:56Z afacostag $


global message edgeSubscriptionResults guiSubscriptionResults ...
    loopSubscriptionResults junctionSubscriptionResults ...
    laneSubscriptionResults detSubscriptionResults ...
    poiSubscriptionResults polygonSubscriptionResults ...
    routeSubscriptionResults simSubscriptionResults ...
    tlSubscriptionResults vehSubscriptionResults ...
    personSubscriptionResults typeSubscriptionResults

modules = {edgeSubscriptionResults guiSubscriptionResults ...
    loopSubscriptionResults junctionSubscriptionResults ...
    laneSubscriptionResults detSubscriptionResults ...
    poiSubscriptionResults polygonSubscriptionResults ...
    routeSubscriptionResults simSubscriptionResults ...
    tlSubscriptionResults vehSubscriptionResults ...
    personSubscriptionResults typeSubscriptionResults};

if nargin < 1
	timeStep = 0;
end
import traci.constants

% Prepare the message to be sent to the SUMO server
message.queue = [message.queue uint8(sscanf(constants.CMD_SIMSTEP2,'%x'))];
message.string = [message.string uint8([1+1+4 sscanf(constants.CMD_SIMSTEP2,'%x')]) ...
    traci.packInt32(timeStep)];

% Send the message
result = traci.sendExact();

% Clear the TraCI subscriptions
for i=1:length(modules)
    if ~isempty(modules{i})
        modules{i}.reset();
    end
end

% Get the number of subscriptions and populate them
numSubs = result.readInt();
responses = cell(1,2*numSubs);
for i=1:numSubs
    [response, objectID] = traci.readSubscription(result);
    responses(2*i-1:2*i) = {response objectID};
end