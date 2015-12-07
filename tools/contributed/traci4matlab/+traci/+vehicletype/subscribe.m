function subscribe(typeID, varargin) 
%subscribe Subscribe to vehicle type variable.
%   subscribe(TYPEID) Subscribe to the VAR_MAXSPEED value for the maximum 
%   allowed interval.
%   subscribe(TYPEID,VARIDS) Subscribe to the values given in the cell 
%   array of strings VARIDS for the maximum allowed interval.
%   subscribe(...,BEGIN) Subscribe from the time BEGIN to the maximum 
%   allowed end time.
%   subscribe(...,END) Subscribe for the time interval defined by BEGIN and 
%   END.
%   A call to this function clears all previous subscription results.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

global typeSubscriptionResults
import traci.constants

% Parse the input
p = inputParser;
p.FunctionName = 'vehicletype.subscribe';
p.addRequired('typeID',@ischar)
p.addOptional('varIDs', {constants.VAR_MAXSPEED}, @iscell)
p.addOptional('subscriptionBegin', 0, @(x)isnumeric(x) && length(x)==1)
p.addOptional('subscriptionEnd', 2^31-1, @(x)isnumeric(x) && length(x)==1)
p.parse(typeID, varargin{:})
typeID = p.Results.typeID;
varIDs = p.Results.varIDs;
subscriptionBegin = p.Results.subscriptionBegin;
subscriptionEnd = p.Results.subscriptionEnd;

typeSubscriptionResults = traci.SubscriptionResults(traci.RETURN_VALUE_FUNC.vehicletype);

typeSubscriptionResults.reset()
traci.subscribe(constants.CMD_SUBSCRIBE_VEHICLETYPE_VARIABLE,...
    subscriptionBegin, subscriptionEnd, typeID, varIDs)