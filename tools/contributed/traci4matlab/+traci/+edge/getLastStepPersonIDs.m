function personIDs = getLastStepPersonIDs(edgeID)
%getLastStepPersonIDs Returns the ids of the persons in the last time step.
%   personIDs = getLastStepPersonIDs(EDGEID) Returns the ids of the persons
%   on the given edge during the last time step.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLastStepPersonIDs.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
personIDs = traci.edge.getUniversal(constants.LAST_STEP_PERSON_ID_LIST, edgeID);