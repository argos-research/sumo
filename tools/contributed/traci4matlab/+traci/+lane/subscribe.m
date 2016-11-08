function subscribe(laneID, varargin) 
%subscribe Subscribe to lane variable.
%   subscribe(LANEID) Subscribe to the LAST_STEP_VEHICLE_NUMBER value for
%   the maximum allowed interval.
%   subscribe(LANEID,VARIDS) Subscribe to the values given in the cell 
%   array of strings VARIDS for the maximum allowed interval.
%   subscribe(...,BEGIN) Subscribe from the time BEGIN to the maximum 
%   allowed end time.
%   subscribe(...,END) Subscribe for the time interval defined by BEGIN and 
%   END.
%   A call to this function clears all previous subscription results.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: subscribe.m 31 2016-09-28 15:16:56Z afacostag $

global laneSubscriptionResults
import traci.constants

% Parse the input
p = inputParser;
p.FunctionName = 'lane.subscribe';
p.addRequired('laneID',@ischar)
p.addOptional('varIDs', {constants.LAST_STEP_VEHICLE_NUMBER}, @iscell)
p.addOptional('subscriptionBegin', 0, @(x)isnumeric(x) && length(x)==1)
p.addOptional('subscriptionEnd', 2^31-1, @(x)isnumeric(x) && length(x)==1)
p.parse(laneID, varargin{:})
laneID = p.Results.laneID;
varIDs = p.Results.varIDs;
subscriptionBegin = p.Results.subscriptionBegin;
subscriptionEnd = p.Results.subscriptionEnd;

laneSubscriptionResults = traci.SubscriptionResults(traci.RETURN_VALUE_FUNC.lane);

laneSubscriptionResults.reset()
traci.subscribe(constants.CMD_SUBSCRIBE_LANE_VARIABLE,...
    subscriptionBegin, subscriptionEnd, laneID, varIDs)