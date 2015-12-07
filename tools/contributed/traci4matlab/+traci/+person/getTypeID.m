function typeID = getTypeID(personID)
%getTypeID Returns the id of the type of the named person.
%   typeID = getTypeID(PERSONID) Returns the id of the type of the named 
%   person.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
typeID = traci.person.getUniversal(constants.VAR_TYPE, personID);